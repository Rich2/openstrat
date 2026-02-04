/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** CSS rule for the body. */
case class CssBody(decsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "body"
}

object CssBody
{ /** Factory apply method for CSS rule for the HTML body. */
  def apply(props: CssDec*): CssBody = new CssBody(props.toArr)
}

/** CSS rule for HTML p paragraphs. */
case class CssP(decsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "p"
}

object CssP
{ /** Factory apply method for CSS rule for p. */
  def apply(props: CssDecs*): CssP = new CssP(props.toArr)
}

/** CSS rule for HTML canvas. */
case class CssCanvas(decsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "canvas"
}

object CssCanvas
{ /** Factory apply method for CSS rule for p. */
  def apply(props: CssDecs*): CssCanvas = new CssCanvas(props.toArr)
}

/** CSS rule for the H1 header. */
case class CssH1(decsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "h1"
}

object CssH1
{ /** Factory apply method for CSS rule for H1 headers. */
  def apply(props: CssDecs*): CssH1 = new CssH1(props.toArr)
}

/** CSS rule for OL ordered lists. */
case class CssOl(decsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "ol"
}

object CssOl
{ /** Factory apply method for CSS rule for the OL ordered list. */
  def apply(props: CssDecs*): CssOl = new CssOl(props.toArr)
}

/** CSS rule for code. */
case class CssCode(decsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "code"
}

object CssCode
{ /** Factory apply method for CSS rule for code. */
  def apply(props: CssDecs*): CssCode = new CssCode(props.toArr)
}

/** CSS rule for code. */
case class CssSvg(decsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "svg"
}

object CssSvg
{ /** Factory apply method for CSS rule for code. */
  def apply(props: CssDecs*): CssSvg = new CssSvg(props.toArr)
}

/** CSS rule for classes. */
trait CssClassesRule extends CssRule
{
  def classStr: String
  override def selec: String = "." + classStr
}

object CssClassesRule
{ /** Factory apply method to create rule for single CSS class with [[RArr]] of [[CssDecs]]. There is a name overload which takes repeat parameters of [[CssDecs]]. */
  def apply(classStr: String, props: RArr[CssDecs]): CssClassesRule = CssClassesRuleGen(classStr, props)

  /** Factory apply method to create rule for single CSS class with [[RArr]] of [[CssDecs]]. There is a name overload which takes an [[RArr]] of [[CssDecs]]. */
  def apply(classStr: String, props: CssDecs*): CssClassesRule = CssClassesRuleGen(classStr, props.toArr)

  case class CssClassesRuleGen(classStr: String, decsArr: RArr[CssDecs]) extends CssClassesRule
}

/** CSS rule for IDs. */
class CssIDRule(val idStr: String, val decsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "#" + idStr
}

object CssIDRule
{ /** Factory apply method to create rule for single CSS ID with [[RArr]] of [[CssDecs]]. There is a name overload which takes repeat parameters of [[CssDecs]]. */
  def apply(classStr: String, props: RArr[CssDecs]): CssIDRule = new CssIDRule(classStr, props)

  /** Factory apply method to create rule for single CSS ID with [[RArr]] of [[CssDecs]]. There is a name overload which takes an [[RArr]] of [[CssDecs]]. */
  def apply(classStr: String, props: CssDecs*): CssIDRule = new CssIDRule(classStr, props.toArr)
}

/** CSS rule for button. */
case class CssButton(decsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "button"
}

object CssButton
{ /** Factory apply method for CSS rule for the HTML button. */
  def apply(props: CssDec*): CssButton = new CssButton(props.toArr)
}