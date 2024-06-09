/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait CssRuleLike
{
  /** Outputs to  a single line if the rule has 2 or more declarations. */
  def isMultiLine: Boolean

  /** The CSS output. */
  def out(indent: Int = 0): String
}

/** Css Rule consisting of selector plus a set of declarations. */
trait CssRule extends CssRuleLike
{ def selec: String
  def propsArr: RArr[CssDecs]


  def propsStr(indent: Int = 0): String =
  {
    val props: RArr[CssDec] = propsArr.flatMap(_.decs)
    props.length match
    { case 0 => " {}"
      case 1 => s" { ${props.head.out} }"
      case 2 => s" { ${props(0).out} ${props(1).out} }"
      case _ => "\n" + (indent).spaces + "{ " + props.foldStr(_.out, "\n" + (indent + 2).spaces) + "\n" + indent.spaces + "}"
    }
  }

  /** Outputs to  a single line if the rule has 2 or more declarations. */
  override def isMultiLine: Boolean = propsArr.flatMap(_.decs).length > 2

  override def out(indent: Int = 0): String = selec + propsStr(indent)
}

object CssRule
{ /** Factory apply method for CSS rule. There is an apply overload where the [[CSSDec]]s are passed as repeat parameters. */
  def apply(selecIn: String, propsIn: RArr[CssDecs]): CssRule = new CssRule
  { override def selec: String = selecIn
    override val propsArr: RArr[CssDecs] = propsIn
  }

  /** Factory apply method for CSS rule. There is an apply overload where the [[CSSDec]]s are passed as an [[RArr]]. */
  def apply(selecIn: String, propsIn: CssDecs*): CssRule = new CssRule
  { override def selec: String = selecIn
    override def propsArr: RArr[CssDecs] = propsIn.toArr
  }
}

/** CSS rule for the body. */
case class CssBody(propsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "body"
}

object CssBody
{ /** Factory apply method for CSS rule for the HTML body. */
  def apply(props: CssDec*): CssBody = new CssBody(props.toArr)
}

/** CSS rule for HTML p paragraphs. */
case class CssP(propsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "p"
}

object CssP
{ /** Factory apply method for CSS rule for p. */
  def apply(props: CssDecs*): CssP = new CssP(props.toArr)
}


/** CSS rule for HTML canvas. */
case class CssCanvas(propsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "canvas"
}

object CssCanvas
{ /** Factory apply method for CSS rule for p. */
  def apply(props: CssDecs*): CssCanvas = new CssCanvas(props.toArr)
}

/** CSS rule for the H1 header. */
case class CssH1(propsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "h1"
}

object CssH1
{ /** Factory apply method for CSS rule for H1 headers. */
  def apply(props: CssDecs*): CssH1 = new CssH1(props.toArr)
}

/** CSS rule for OL ordered lists. */
case class CssOl(propsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "ol"
}

object CssOl
{ /** Factory apply method for CSS rule for the OL ordered list. */
  def apply(props: CssDecs*): CssOl = new CssOl(props.toArr)
}

/** CSS rule for code. */
case class CssCode(propsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "code"
}

object CssCode
{ /** Factory apply method for CSS rule for code. */
  def apply(props: CssDecs*): CssCode = new CssCode(props.toArr)
}

/** CSS rule for code. */
case class CssSvg(propsArr: RArr[CssDecs]) extends CssRule
{
  override def selec: String = "svg"
}

object CssSvg
{
  /** Factory apply method for CSS rule for code. */
    def apply(props: CssDecs*): CssSvg = new CssSvg(props.toArr)
}

class CssClassesRule(val classStr: String, val propsArr: RArr[CssDecs]) extends CssRule
{
  override def selec: String = "." + classStr
}

object CssClassesRule
{
  def apply(classStr: String, props: RArr[CssDecs]): CssClassesRule = new CssClassesRule(classStr, props)
  def apply(classStr: String, props: CssDecs*): CssClassesRule = new CssClassesRule(classStr, props.toArr)
}

class CssObjectRule(val classStr: String, val propsArr: RArr[CssDecs]) extends CssRule
{
  override def selec: String = "#" + classStr
}

object CssObjectRule
{
  def apply(classStr: String, props: RArr[CssDecs]): CssObjectRule = new CssObjectRule(classStr, props)
  def apply(classStr: String, props: CssDecs*): CssObjectRule = new CssObjectRule(classStr, props.toArr)
}

/** CSS rule for button. */
case class CssButton(propsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "button"
}

object CssButton
{ /** Factory apply method for CSS rule for the HTML button. */
  def apply(props: CssDec*): CssButton = new CssButton(props.toArr)
}