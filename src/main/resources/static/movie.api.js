export const BASE_URL = "http://localhost:8080/api/movies/";

const BASE_URL_MOVIES = `${BASE_URL}`;

export async function fetchMovies() {
    const response = await fetch(`${BASE_URL_MOVIES}`);
    if (!response.ok) {
        throw new Error('Failed to fetch movies');
    }
    return response.json();
}

export async function createMovie(movie) {
    const response = await fetch(`${BASE_URL_MOVIES}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(movie),
    });
    if (!response.ok) {
        throw new Error('Failed to add movie');
    }
    return response.json();
}

export async function updateMovie(movieId, movie) {
    const response = await fetch(`${BASE_URL_MOVIES}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(movie),
    });
    if (!response.ok) {
        throw new Error('Failed to update movie');
    }
    return response.json();
}

export async function deleteMovie(movieId) {
    const response = await fetch(`${BASE_URL_MOVIES}${movieId}`, {
        method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error('Failed to delete movie');
    }
}