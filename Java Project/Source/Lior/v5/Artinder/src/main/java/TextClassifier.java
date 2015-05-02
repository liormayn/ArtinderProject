/**
 * Created by Lior on 24/04/2015.
 */
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.*;
import weka.core.converters.*;
import weka.classifiers.trees.*;
import weka.core.stemmers.LovinsStemmer;
import weka.core.tokenizers.AlphabeticTokenizer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.*;
import weka.filters.supervised.attribute.AddClassification;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.*;

import java.io.File;

public class TextClassifier {


    //everytime a user enters.
    // we will create folders of articles by his classifications and find a new article.
    //each time we will create a classifier and classify.
    /**
     * Expects the first parameter to point to the directory with the text files.
     * In that directory, each sub-directory represents a class and the text
     * files in these sub-directories will be labeled as such.
     *
     * @param args        the commandline arguments
     * @throws Exception  if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        // convert the directory into a dataset
        TextDirectoryLoader loader = new TextDirectoryLoader();
        loader.setDirectory(new File(args[0]));
        Instances dataRaw = loader.getDataSet();
        //System.out.println("\n\nImported data:\n\n" + dataRaw);

        // apply the StringToWordVector
        // (see the source code of setOptions(String[]) method of the filter
        // if you want to know which command-line option corresponds to which
        // bean property)
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(dataRaw);
        //add to filter - words to keep counter, tfidf,stemmer?
        //10-14 minutes in video
        filter.setIDFTransform(true);
        filter.setTFTransform(true);
        filter.setOutputWordCounts(true);
        // the number is the number for positive , and the number for negative.
        // if the user has 3 - it's 3 words for positive and 3 words for negative
        filter.setWordsToKeep(1000);

        //find out what this means, i think it treats all words as lower case
        filter.setLowerCaseTokens(true);

        //GETTING LEMAS
        filter.setStemmer(new LovinsStemmer());

        //word unit
        filter.setTokenizer(new AlphabeticTokenizer());

        Instances dataFiltered = Filter.useFilter(dataRaw, filter);
        System.out.println("\n\nFiltered data:\n\n" + dataFiltered);

        //add to filter - words to keep counter, tfidf,stemmer?
        // train J48 and output model
        SMO classifier = new SMO();
        //classifier.
        classifier.buildClassifier(dataFiltered);

        System.out.println("\n\nClassifier model:\n\n" + classifier);


        // how to classify articles using this classifier?
        TextDirectoryLoader secondLoader = new TextDirectoryLoader();
        secondLoader.setDirectory(new File(args[1]));
       Instances seconddataRaw = secondLoader.getDataSet();

        FilteredClassifier Secondclassifier = new FilteredClassifier();
        Secondclassifier.setFilter(filter);
        Secondclassifier.setClassifier(new SMO());


        System.out.println("\n\nClassifications:");

        Instances processed_test_data = Filter.useFilter(seconddataRaw, filter);

        for (int i = 0; i < processed_test_data.numInstances(); i++) {
            weka.core.Instance currentInst = processed_test_data.instance(i);
            System.out.println("");
            System.out.println("Article:");
            System.out.println(seconddataRaw.instance(i));
            int predictedClass = (int) classifier.classifyInstance(currentInst);
            System.out.println("Classification:");
            System.out.println(predictedClass);
        }


    }
}
