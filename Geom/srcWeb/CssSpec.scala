/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait CssRulesHolder
{
  /** The CSS rules. */
  def rules: RArr[CssRuleLike]

  def rulesOut: String = rules.length match
  { case 0 => ""
    case 1 => rules(0).out
    case _ =>
    { var acc: String = rules(0).out
      var prev: Boolean = rules(0).isMultiLine
      iUntilForeach(1, rules.length){ i =>
        val curr = rules(i)
        if(prev || curr.isMultiLine)  acc = acc ---- curr.out else acc = acc --- curr.out
        prev = curr.isMultiLine
      }
      acc
    }
  }
}

/** A list of CssRules and media queries with a possibe end [[String]]. */
trait CssSpec extends CssRulesHolder
{

  /** A [[String at the end of the output to add CSS code that has not been converted into Scala.]] */
  def endStr: String = ""

  def apply(): String = { val s1 = rulesOut; ife(s1 == "", endStr, s1 ---- endStr) }

  /*  rules.length match
  { case 0 => endStr
    case 1 => rules(0).out --- endStr
    case _ =>
    { var acc: String = rules(0).out
      var prev: Boolean = rules(0).isMultiLine
      iUntilForeach(1, rules.length){ i =>
        val curr = rules(i)
        if(prev || curr.isMultiLine)  acc = acc ---- curr.out else acc = acc --- curr.out
        prev = curr.isMultiLine
      }
      acc ---- endStr
    }
  }*/
}