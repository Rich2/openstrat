/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait CssRuleLike
{
  /** Outputs to  a single line if the rule has 2 or more declarations. */
  def isMultiLine: Boolean

  /** The CSS output. */
  def out: String
}

/** Css Rule consisting of selector plus a set of declarations. */
trait CssRule extends CssRuleLike
{ def selec: String
  def props: RArr[CssDec]

  def propsStr: String = props.length match
  { case 0 => " {}"
    case 1 => s" { ${props.head.out} }"
    case 2 => s" { ${props(0).out} ${props(1).out} }"
    case _ => "\n{ " + props.foldStr(_.out, "\n  ") + " \n}"
  }

  /** Outputs to  a single line if the rule has 2 or more declarations. */
  override def isMultiLine: Boolean = props.length > 2

  override def out: String = selec + propsStr
}

object CssRule
{ /** Factory apply method for CSS rule. There is an apply overload where the [[CSSDec]]s are passed as repeat parameters. */
  def apply(selecIn: String, propsIn: RArr[CssDec]): CssRule = new CssRule {
    override def selec: String = selecIn
    override def props: RArr[CssDec] = propsIn
  }

  /** Factory apply method for CSS rule. There is an apply overload where the [[CSSDec]]s are passed as an [[RArr]]. */
  def apply(selecIn: String, propsIn: CssDec*): CssRule = new CssRule {
    override def selec: String = selecIn
    override def props: RArr[CssDec] = propsIn.toArr
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
  override def selec: String = "." + classStr
}

object CssClassesRule
{
  def apply(classStr: String, props: RArr[CssDec]): CssClassesRule = new CssClassesRule(classStr, props)
  def apply(classStr: String, props: CssDec*): CssClassesRule = new CssClassesRule(classStr, props.toArr)
}

class CssObjectRule(val classStr: String, val props: RArr[CssDec]) extends CssRule
{
  override def selec: String = "#" + classStr
}

object CssObjectRule
{
  def apply(classStr: String, props: RArr[CssDec]): CssObjectRule = new CssObjectRule(classStr, props)
  def apply(classStr: String, props: CssDec*): CssObjectRule = new CssObjectRule(classStr, props.toArr)
}