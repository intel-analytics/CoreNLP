package edu.stanford.nlp.tagger.maxent.documentation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/** This demo shows user-provided sentences (i.e., {@code List<HasWord>})
 *  being tagged by the tagger. The sentences are generated by direct use
 *  of the DocumentPreprocessor class.
 *
 *  @author Christopher Manning
 */
class TaggerDemo2 {

  private TaggerDemo2() {}

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("usage: java TaggerDemo modelFile fileToTag");
      return;
    }
    MaxentTagger tagger = new MaxentTagger(args[0]);
    TokenizerFactory<CoreLabel> ptbTokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(),
									   "untokenizable=noneKeep");
    BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(args[1]), "utf-8"));
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, "utf-8"));
    DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(r);
    documentPreprocessor.setTokenizerFactory(ptbTokenizerFactory);
    for (List<HasWord> sentence : documentPreprocessor) {
      List<TaggedWord> tSentence = tagger.tagSentence(sentence);
      pw.println(Sentence.listToString(tSentence, false));
    }
    pw.close();
  }

}
