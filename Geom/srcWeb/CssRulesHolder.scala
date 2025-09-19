/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Base trait for [[CssMedia]] and [[CssRulesWithString]]. */
trait CssRulesHolder extends HttpContent
{
  /** The CSS rules. */
  def rules: RArr[CssRuleLike]

  def apply(): String = rulesOut(0)

  def rulesOut(indent: Int = 0): String = rules.length match
  { case 0 => ""
    case 1 => indent.spaces + rules(0).out(indent)
    case _ =>
    { var acc: String = indent.spaces + rules(0).out(indent)
      var prev: Boolean = rules(0).isMultiLine
      iUntilForeach(1, rules.length){ i =>
        val curr = rules(i)
        if(prev || curr.isMultiLine)  acc = acc ---- indent.spaces + curr.out(indent) else acc = acc --- indent.spaces + curr.out(indent)
        prev = curr.isMultiLine
      }
      acc
    }
  }

  override def out: String = rulesOut(0)
  override def httpResp(dateStr: String, server: String): HttpFound = HttpFound(dateStr, server, HttpConTypeCss, out)
}

/** A list of CssRules and media queries with a possible end [[String]]. */
trait CssRulesWithString extends CssRulesHolder
{ /** A [[String]] at the end of the output to add CSS code that has not been converted into Scala. */
  def endStr: String = ""

  override def apply(): String =
  { val s1 = rulesOut()
    s1 match
    { case "" => endStr
      case s1 if endStr == "" => s1
      case s1 => s1 ---- endStr
    }
  }
}