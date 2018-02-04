package org.ankur.meetup.demo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class ClassifierTrainer {

	private static final String TRAINING_DATA_FILE = "training.data"; //$NON-NLS-1$
	private static final String TRAINING_DATA_FOLDER = "src//main//data"; //$NON-NLS-1$
	private static final String MODEL_LOCATION = "src/main/models"; //$NON-NLS-1$
	private static final String EN_CLASSIFIER_MODEL = "en-classifier.bin"; //$NON-NLS-1$

	public static void main(String[] args) {
//		trainModel();
		classifierExample();
	}

	private static void trainModel() {
		try (OutputStream modelOutputStream = new BufferedOutputStream(
				new FileOutputStream(new File(MODEL_LOCATION, EN_CLASSIFIER_MODEL)))) {
			File trainingFile = new File(TRAINING_DATA_FOLDER, TRAINING_DATA_FILE);
			Charset charset = Charset.forName("UTF-8"); //$NON-NLS-1$
			MarkableFileInputStreamFactory factory = new MarkableFileInputStreamFactory(trainingFile);
			ObjectStream<String> lineStream = new PlainTextByLineStream(factory, charset);
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
			DoccatFactory dFactory = new DoccatFactory();
			DoccatModel model = DocumentCategorizerME.train("en", sampleStream, TrainingParameters.defaultParams(), //$NON-NLS-1$
					dFactory);
			model.serialize(modelOutputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static void classifierExample() {
		String movie = "american gang";
		
		String genre = ClassifierTrainer.classify(movie.split(" "));
		System.out.println(genre);
	}

	public static String classify(String[] input) {
		String category = null;

		try {
			DoccatModel model = new DoccatModel(new File(MODEL_LOCATION, EN_CLASSIFIER_MODEL));
			DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

			double[] outcomes = myCategorizer.categorize(input);
			System.out.println(Arrays.toString(outcomes));
			category = myCategorizer.getBestCategory(outcomes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return category;
	}

}
