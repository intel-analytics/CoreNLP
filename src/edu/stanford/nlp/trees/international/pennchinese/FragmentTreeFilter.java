package edu.stanford.nlp.trees.international.pennchinese;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.Filter;

/**
 * Filters the fragments which end documents in Chinese Treebank
 */
public class FragmentTreeFilter implements Filter<Tree> {
  static final TregexPattern threeNodePattern = 
    TregexPattern.compile("FRAG=root <, (PU <: /（/) <2 (VV <: /完/) <- (PU=a <: /）/) <3 =a : =root !> (__ > __)");

  static final TregexPattern oneNodePattern =
    TregexPattern.compile("FRAG=root <: (VV <: /完/) : =root !> (__ > __)");

  static final TregexPattern automaticInitialPattern =
    TregexPattern.compile("automatic=root <: (initial !< __) : =root !> __");

  static final TregexPattern manuallySegmentedPattern =
    TregexPattern.compile("manually=root <: (segmented !< __) : =root !> __");

  static final TregexPattern onthewayPattern =
    TregexPattern.compile("FRAG=root <: (NR <: (ontheway !< __)) : =root !> (__ > __)");

  static final TregexPattern[] patterns = { threeNodePattern, oneNodePattern, automaticInitialPattern, manuallySegmentedPattern, onthewayPattern };

  public boolean accept(Tree tree) {
    for (TregexPattern pattern : patterns) {
      if (pattern.matcher(tree).find()) {
        return false;
      }
    }
    return true;
  }

  private static final long serialVersionUID = 1L;
}
