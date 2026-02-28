/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Base trait for [[CssMedia]] and [[CssRulesWithString]]. */
trait CssRulesHolder extends HttpContent
{
  /** The CSS rules. */
  def rules: RArr[CssRuleLike]

  def apply(): String = rulesOut(0)

  override def out: String = rulesOut(0)

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
  
  override def httpResp(dateStr: String, server: String): HttpFound = HttpFound(dateStr, server, HttpConTypeCss, out)
}