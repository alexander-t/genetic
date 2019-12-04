package se.tarlinder.genetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 100;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new StringChromosome());
        }

        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {

            population.sort(Comparator.comparingInt(Chromosome::calculateFitness));

            // Elitism
            List<Chromosome> nextGeneration = population.stream().limit(10 * POPULATION_SIZE / 100).collect(Collectors.toList());
            System.err.println(generation + ":" + nextGeneration);

            // Solution found
            if (nextGeneration.get(0).calculateFitness() == 0) {
                return;
            }

            // Offspring
            List<Chromosome> fittestHalf = population.stream().limit(POPULATION_SIZE / 2).collect(Collectors.toList());
            for (int i = 0; i < 90 * POPULATION_SIZE / 100; i++) {
                Chromosome parent1 = fittestHalf.get(RANDOM.nextInt(POPULATION_SIZE / 2));
                Chromosome parent2 = fittestHalf.get(RANDOM.nextInt(POPULATION_SIZE / 2));
                Chromosome child = parent1.mate(parent2);
                System.err.println(parent1 + " <X> " + parent2 + " => " + child);
                nextGeneration.add(child);
            }

            population = nextGeneration;
        }
    }
}
