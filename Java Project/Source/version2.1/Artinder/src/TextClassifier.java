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
        filter.setWordsToKeep(6);

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

       // Secondclassifier.classifyInstance()
       // secondLoader.
        //System.out.println("\n\nImported data:\n\n" + dataRaw);

        // apply the StringToWordVector
        // (see the source code of setOptions(String[]) method of the filter
        // if you want to know which command-line option corresponds to which
        // bean property)
       // StringToWordVector secondfilter = new StringToWordVector();
       // secondfilter.setInputFormat(seconddataRaw);
        //add to filter - words to keep counter, tfidf,stemmer?
        //10-14 minutes in video
       // secondfilter.setIDFTransform(true);
       // secondfilter.setTFTransform(true);
      //  secondfilter.setOutputWordCounts(true);
        // the number is the number for positive , and the number for negative.
        // if the user has 3 - it's 3 words for positive and 3 words for negative
       // secondfilter.setWordsToKeep(6);
        //secondfilter.c

        //bullshit
       // FilteredClassifier filteredClassifier = new FilteredClassifier();
       // filteredClassifier.buildClassifier(dataFiltered);
        //filteredClassifier.setClassifier(classifier);
       // double filteredInstance = filteredClassifier.classifyInstance(seconddataRaw.firstInstance());
        //bullshit

        Instances predictedData = null;
        AddClassification thirdfilter = new AddClassification();
        thirdfilter.setClassifier(classifier);
        thirdfilter.setOutputClassification(true);
        thirdfilter.setOutputDistribution(true);
        thirdfilter.setOutputErrorFlag(true);
        thirdfilter.setInputFormat(dataFiltered);
        thirdfilter.useFilter(dataFiltered, thirdfilter);  // trains the classifier

        System.out.println("\n\nSecondDataRaw:\n\n" + seconddataRaw);

       // secondfilter
      //  Instances pred = Filter.useFilter(s, thirdfilter);
        //Instances pred = Filter.input(seconddataRaw, thirdfilter);  // perform predictions on test set
Instances pred = null;
        Secondclassifier.buildClassifier(dataFiltered);
        double a = Secondclassifier.classifyInstance(seconddataRaw.firstInstance());

        //boolean predd = thirdfilter.input(seconddataRaw.firstInstance());

        if (predictedData == null)
            predictedData = new Instances(pred, 0);
        for (int j = 0; j < pred.numInstances(); j++)
            predictedData.add(pred.instance(j));

        System.out.println("\n\nSecondDataRaw:\n\n" + seconddataRaw);
        System.out.println("\n\nPred:\n\n" + pred);
        if (pred.numInstances() >0) {
            System.out.println("\n\nPred First:\n\n" + pred.firstInstance());
            System.out.println("\n\nPredicted data:\n\n" + predictedData);
            classifier.classifyInstance(pred.firstInstance());
        }


        //find out what this means, i think it treats all words as lower case
        //secondfilter.setLowerCaseTokens(true);

        //GETTING LEMAS
        //secondfilter.setStemmer(new LovinsStemmer());

        //word unit
        //secondfilter.setTokenizer(new AlphabeticTokenizer());



        //Instances seconddataFiltered = Filter.useFilter(seconddataRaw, secondfilter);
       // System.out.println("\n\nFiltered data:\n\n" + seconddataFiltered);

        //classifier.classifyInstance(seconddataFiltered.instance(0));
    }
}
