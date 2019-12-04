package se.tarlinder.genetic;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class StringChromosome extends Chromosome<String> {

    private Random random = new Random();

    public StringChromosome() {
        genes = create();
    }

    private StringChromosome(String genes) {
        this.genes = genes;
    }

    @Override
    public String create() {
        return RandomStringUtils.randomAlphabetic(StringOptimization.TARGET.length());
    }

    public int calculateFitness() {
        int fitness = 0;
        for (int i = 0; i < genes.length(); i++) {
            if (genes.charAt(i) != StringOptimization.TARGET.charAt(i)) {
                fitness++;
            }
        }
        return fitness;
    }

    @Override
    public String toString() {
        return genes + " (" + calculateFitness() + ")";
    }

    public StringChromosome mate(Chromosome<String> otherParent) {
        String genes = "";
        for (int i = 0; i < StringOptimization.TARGET.length(); i++) {
            double p = random.nextDouble();
            if (p < 0.45) {
                genes += this.genes.charAt(i);
            } else if (p >= 0.45 && p < 0.9) {
                genes += otherParent.genes.charAt(i);
            } else {
                genes += RandomStringUtils.randomAlphabetic(1);
            }
        }
        return new StringChromosome(genes);
    }
}
