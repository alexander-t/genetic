package se.tarlinder.genetic;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    private static final String TARGET = "HelloWorld";
    private static final int POPULATION_SIZE = 100;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new Chromosome());
        }

        for (int gen = 0; gen < 50; gen++) {

            population.sort(Comparator.comparingInt(Chromosome::calculateFitness));

            // Elitism
            List<Chromosome> nextGeneration = population.stream().limit(10 * POPULATION_SIZE / 100).collect(Collectors.toList());
            System.err.println(gen + ":" + nextGeneration);
            if (nextGeneration.get(0).calculateFitness() == 0) {
                return;
            }

            // Offspring
            List<Chromosome> fittestHalf = population.stream().limit(POPULATION_SIZE / 2).collect(Collectors.toList());
            for (int i = 0; i < 90 * POPULATION_SIZE / 100; i++) {
                Chromosome parent1 = fittestHalf.get(RANDOM.nextInt(POPULATION_SIZE / 2));
                Chromosome parent2 = fittestHalf.get(RANDOM.nextInt(POPULATION_SIZE / 2));
                Chromosome child = parent1.mate(parent2);
                System.err.println(parent1 + " X " + parent2 + " => " + child);
                nextGeneration.add(child);
            }

            population = nextGeneration;
        }
    }

    static class Chromosome {
        private String genes;

        public Chromosome() {
            this(RandomStringUtils.randomAlphabetic(TARGET.length()));
        }

        private Chromosome(String genes) {
            this.genes = genes;
        }

        public int calculateFitness() {
            int fitness = 0;
            for (int i = 0; i < genes.length(); i++) {
                if (genes.charAt(i) != TARGET.charAt(i)) {
                    fitness++;
                }
            }
            return fitness;
        }

        @Override
        public String toString() {
            return genes + " (" + calculateFitness() + ")";
        }

        public Chromosome mate(Chromosome parent2) {
            String genes = "";
            for (int i = 0; i < TARGET.length(); i++) {
                double p = RANDOM.nextDouble();
                if (p < 0.45) {
                    genes += this.genes.charAt(i);
                } else if (p >= 0.45 && p < 0.9) {
                    genes += parent2.genes.charAt(i);
                } else {
                    genes += RandomStringUtils.randomAlphabetic(1);
                }
            }
            return new Chromosome(genes);
        }
    }
}
