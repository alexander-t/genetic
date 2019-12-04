package se.tarlinder.genetic;

public abstract class Chromosome<T> {
    protected T genes;
    public abstract T create();
    public abstract int calculateFitness();
    public abstract Chromosome<T> mate(Chromosome<T> otherParent);
}
