/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** CSS rule for the body. */
case class CssBody(decsArr: RArr[CssDecBase]) extends CssRule
{ override def selec: String = "body"
}

object CssBody
{ /** Factory apply method for CSS rule for the HTML body. */
  def apply(props: CssDec*): CssBody = new CssBody(props.toArr)
}

/** CSS rule for HTML p paragraphs. */
case class CssP(decsArr: RArr[CssDecBase]) extends CssRule
{ override def selec: String = "p"
}

object CssP
{ /** Factory apply method for CSS rule for p. */
  def apply(props: CssDecBase*): CssP = new CssP(props.toArr)
}

/** CSS rule for HTML canvas. */
case class CssCanvas(decsArr: RArr[CssDecBase]) extends CssRule
{ override def selec: String = "canvas"
}

object CssCanvas
{ /** Factory apply method for CSS rule for p. */
  def apply(props: CssDecBase*): CssCanvas = new CssCanvas(props.toArr)
}

/** CSS rule for the H1 header. */
case class CssH1(decsArr: RArr[CssDecBase]) extends CssRule
{ override def selec: String = "h1"
}

object CssH1
{ /** Factory apply method for CSS rule for H1 headers. */
  def apply(props: CssDecBase*): CssH1 = new CssH1(props.toArr)
}

/** CSS rule for OL ordered lists. */
case class CssOl(decsArr: RArr[CssDecBase]) extends CssRule
{ override def selec: String = "ol"
}

object CssOl
{ /** Factory apply method for CSS rule for the OL ordered list. */
  def apply(props: CssDecBase*): CssOl = new CssOl(props.toArr)
}

/** CSS rule for code. */
case class CssCode(decsArr: RArr[CssDecBase]) extends CssRule
{ override def selec: String = "code"
}

object CssCode
{ /** Factory apply method for CSS rule for code. */
  def apply(props: CssDecBase*): CssCode = new CssCode(props.toArr)
}

/** CSS rule for code. */
case class CssSvg(decsArr: RArr[CssDecBase]) extends CssRule
{ override def selec: String = "svg"
}

object CssSvg
{ /** Factory apply method for CSS rule for code. */
  def apply(props: CssDecBase*): CssSvg = new CssSvg(props.toArr)
}

/** CSS rule for classes. */
trait CssClassesRule extends CssRule
{
  def classStr: String
  override def selec: String = "." + classStr
}

object CssClassesRule
{ /** Factory apply method to create rule for single CSS class with [[RArr]] of [[CssDecBase]]. There is a name overload which takes repeat parameters of [[CssDecBase]]. */
  def apply(classStr: String, props: RArr[CssDecBase]): CssClassesRule = CssClassesRuleGen(classStr, props)

  /** Factory apply method to create rule for single CSS class with [[RArr]] of [[CssDecBase]]. There is a name overload which takes an [[RArr]] of [[CssDecBase]]. */
  def apply(classStr: String, props: CssDecBase*): CssClassesRule = CssClassesRuleGen(classStr, props.toArr)

  case class CssClassesRuleGen(classStr: String, decsArr: RArr[CssDecBase]) extends CssClassesRule
}

/** CSS rule for IDs. */
class CssIDRule(val idStr: String, val decsArr: RArr[CssDecBase]) extends CssRule
{ override def selec: String = "#" + idStr
}

object CssIDRule
{ /** Factory apply method to create rule for single CSS ID with [[RArr]] of [[CssDecBase]]. There is a name overload which takes repeat parameters of [[CssDecBase]]. */
  def apply(classStr: String, props: RArr[CssDecBase]): CssIDRule = new CssIDRule(classStr, props)

  /** Factory apply method to create rule for single CSS ID with [[RArr]] of [[CssDecBase]]. There is a name overload which takes an [[RArr]] of [[CssDecBase]]. */
  def apply(classStr: String, props: CssDecBase*): CssIDRule = new CssIDRule(classStr, props.toArr)
}

/** CSS rule for button. */
case class CssButton(decsArr: RArr[CssDecBase]) extends CssRule
{ override def selec: String = "button"
}

object CssButton
{ /** Factory apply method for CSS rule for the HTML button. */
  def apply(props: CssDec*): CssButton = new CssButton(props.toArr)
}