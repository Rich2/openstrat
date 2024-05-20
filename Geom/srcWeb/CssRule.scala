/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Css Rule consisting of selector plus a set of declarations. */
trait CssRule
{ def selec: String
  def props: RArr[CssDec]

  def propsStr: String = props.length match
  { case 0 => " {}"
    case 1 => s" { ${props.head.out} }"
    case _ => "\n{ " + props.foldStr(_.out, "\n  ") + " \n}"
  }

  def isMultiLine = props.length > 1
  def out: String = selec + propsStr
}

/** A list of CssRules with a possibe end [[String]]. */
trait CssRules
{ /** The CSS rules. */
  def rules: RArr[CssRule]

  /** A [[String at the end of the output to add CSS code that has not been converted into Scala.]] */
  def endStr: String = ""

  def apply(): String = rules.length match
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
  }
}

/** CSS rule for the body. */
case class CssBody(props: RArr[CssDec]) extends CssRule
{ override def selec: String = "body"
}

object CssBody
{ /** Factory apply method for CSS rule for the HTML body. */
  def apply(props: CssDec*): CssBody = new CssBody(props.toArr)
}

/** CSS rule for the H1 header. */
case class CssH1(props: RArr[CssDec]) extends CssRule
{ override def selec: String = "h1"
}

object CssH1
{ /** Factory apply method for CSS rule for H1 headers. */
  def apply(props: CssDec*): CssH1 = new CssH1(props.toArr)
}

/** CSS rule for OL ordered lists. */
case class CssOl(props: RArr[CssDec]) extends CssRule
{ override def selec: String = "ol"
}

object CssOl
{ /** Factory apply method for CSS rule for the OL ordered list. */
  def apply(props: CssDec*): CssOl = new CssOl(props.toArr)
}

/** CSS rule for code. */
case class CssCode(props: RArr[CssDec]) extends CssRule
{ override def selec: String = "code"
}

object CssCode
{ /** Factory apply method for CSS rule for code. */
  def apply(props: CssDec*): CssCode = new CssCode(props.toArr)
}

class CssClassesRule(val classStr: String, val props: RArr[CssDec]) extends CssRule
{
  def selec: String = "." + classStr
}

object CssClassesRule
{

}