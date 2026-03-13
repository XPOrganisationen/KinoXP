import {fetchMovies, createMovie, updateMovie, deleteMovie} from "./movie.api.js";

export function MovieStore() {
    let movies = [];
    let isLoading = false;
    let error = null;
    const subscribers = [];

    const subscribe = (callback) => {
        subscribers.push(callback);
        callback({ movies, isLoading, error });

        return () => {
            const index = subscribers.indexOf(callback);
            if (index > -1) {
                subscribers.splice(index, 1);
            }
        };
    };

    const unsubscribe = (callback) => {
        const index = subscribers.indexOf(callback);
        if (index > -1) {
            subscribers.splice(index, 1);
        }
    };

    const notify = () => {
        subscribers.forEach(callback => {
            callback({ movies, isLoading, error });
        });
    };

    async function loadMovies(isSilent = false) {
        error = null;
        if (!isSilent) {
            isLoading = true;
            notify();
        }
        try {
            movies = await fetchMovies();
        } catch (err) {
            error = {
                message: "Failed to fetch movies. Please try again.",
                type: "FetchError"
            };
        } finally {
            isLoading = false;
        }
        notify();
    }

    async function addMovie(movie) {
        error = null;
        const tempId = -Date.now();
        const optimisticMovie = { ...movie, movieId: tempId };
        movies = [...movies, optimisticMovie];
        notify();

        try {
            const newMovie = await createMovie(movie);

            movies = movies.map(e => e.movieId == tempId ? newMovie : e);
            error = null;
        } catch (err) {
            error = {
                message: "Failed to create movie. Please try again.",
                type: "CreateError"
            };
            movies = movies.filter(t => t.movieId !== tempId);
        }
        notify();
    }

    async function changeMovie(movieId, movie) {
        error = null;
        const previousMovies = [...movies];

        movies = movies.map(t =>
            t.movieId === Number(movieId) ? { ...movie, movieId: Number(movieId) } : t
        );
        notify();

        try {
            await updateMovie(movieId, movie);
            error = null;
        } catch (err) {
            error = {
                message: "Failed to update movie. Please try again.",
                type: "UpdateError"
            };
            movies = previousMovies;
        }
        notify();
    }

    async function removeMovie(movieId) {
        error = null;
        const previousMovies = [...movies];

        movies = movies.filter(movie => movie.movieId !== Number(movieId));
        notify();

        try {
            await deleteMovie(movieId);
            error = null;
        } catch (err) {
            error = {
                message: "Failed to delete movie. Please try again.",
                type: "DeleteError"
            };
            movies = previousMovies;
        }
        notify();
    }

    const getMovieById = (movieId) => {
        let movie = movies.find(movie => movie.movieId === Number.parseInt(movieId));
        return movie;
    };

    const init = async () => {
        await loadMovies();
    };

    return {
        subscribe,
        unsubscribe,
        init,
        addMovie,
        changeMovie,
        removeMovie,
        getMovieById
    };
}