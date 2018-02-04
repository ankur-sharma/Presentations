package org.ankur.meetup.demo;

import java.io.File;
import java.io.IOException;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class POSTagger {

	private static final String MODEL_LOCATION = "src/main/models"; //$NON-NLS-1$
	private static final String EN_CLASSIFIER_MODEL = "en-pos-maxent.bin"; //$NON-NLS-1$

	
	public static void main(String[] args) {
		posTaggerExample();
	}
	
	private static void posTaggerExample() {
		String sentence = "Quick brown fox jumped over the lazy dog";
		
		String[] words = sentence.split(" ");
		String[] tags = POSTagger.GetPOSTags(words);
		for (int i = 0; i < words.length; i++) {
			System.out.println(words[i] + "\t\t" + tags[i]);
		}
	}
	
	public static String[] GetPOSTags(String[] input) {
		try {
			POSModel model = new POSModel(new File(MODEL_LOCATION, EN_CLASSIFIER_MODEL));
			POSTaggerME posTagger = new POSTaggerME(model);
			return posTagger.tag(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String[] {};
	}

}
