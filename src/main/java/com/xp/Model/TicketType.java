package com.xp.Model;

public enum TicketType {
    CHILD(75),
    ADULT(140),
    SENIOR(90);

    private final double price;

    TicketType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

//koden nedenunder er kun hvis vi laver en login baseret system hvor når man skriver sin alder, tjekker systemet nummeret -
// - og tilføjer dig til den ticket type din alder er i (barne billet, voksen billet eller pensionist)
/*public enum TicketType {
    CHILD(0, 16, 75),
    ADULT(17, 64, 140),
    SENIOR(65, 100, 90 );

    private final int minAge;
    private final int maxAge;
    private final double price;

    TicketType(int minAge, int maxAge, double price) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.price = price;
    }

    public boolean matchesAge(int age) {
        return age >= minAge && age <= maxAge;
    }

    public double getPrice() {
        return price;
    }

}

 */
