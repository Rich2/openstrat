/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait CssRuleLike extends XConElem
{ /** Outputs to  a single line if the rule has 2 or more declarations. */
  def isMultiLine: Boolean

  /** The CSS output. */
  def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = MaxLineLen): String
}

/** Css Rule consisting of selector plus a set of declarations. */
trait CssRule extends CssRuleLike
{ /** The selector [[String]] for the CSS rule. */
  def selec: String

  /** The CSS declarations of this rule. */
  def decsArr: RArr[CssDecs]

  /** The inner [[String]] of this rule's declarations. */
  def decsStr(indent: Int = 0): String =
  { val decs: RArr[CssDec] = decsArr.flatMap(_.decs)
    decs.length match
    { case 0 => " {}"
      case 1 => s" { ${decs.head.out} }"
      case 2 => s" { ${decs(0).out} ${decs(1).out} }"
      case _ => "\n" + (indent).spaces + "{ " + decs.mkStr(_.out, "\n" + (indent + 2).spaces) + "\n" + indent.spaces + "}"
    }
  }

  override def isMultiLine: Boolean = decsArr.flatMap(_.decs).length > 2
  override def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = MaxLineLen): String = selec + decsStr(indent)
  
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines =
  { val decs: RArr[CssDec] = decsArr.flatMap(_.decs)
    decs.length match
    { case 0 => TextLines(Array(selec -- "{}"))
      case 1 =>
      { val str = selec -- s" { ${decs.head.out} }"
        TextLines(Array(str))
      }
      case 2 =>
      { val str = s" { ${decs(0).out} ${decs(1).out} }"
        TextLines(Array(str))
      }
      case _ =>
      { val str = "\n" + (indent).spaces + "{ " + decs.mkStr(_.out, "\n" + (indent + 2).spaces) + "\n" + indent.spaces + "}"
        TextLines(Array(str))
      }
    }
  }
}

object CssRule
{ /** Factory apply method for CSS rule. There is an apply overload where the [[CSSDec]]s are passed as repeat parameters. */
  def apply(selecIn: String, propsIn: RArr[CssDecs]): CssRule = new CssRule
  { override def selec: String = selecIn
    override val decsArr: RArr[CssDecs] = propsIn
  }

  /** Factory apply method for CSS rule. There is an apply overload where the [[CSSDec]]s are passed as an [[RArr]]. */
  def apply(selecIn: String, propsIn: CssDecs*): CssRule = new CssRule
  { override def selec: String = selecIn
    override def decsArr: RArr[CssDecs] = propsIn.toArr
  }
}

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
class CssClassesRule(val classStr: String, val decsArr: RArr[CssDecs]) extends CssRule
{ override def selec: String = "." + classStr
}

object CssClassesRule
{ /** Factory apply method to create rule for single CSS class with [[RArr]] of [[CssDecs]]. There is a name overload which takes repeat parameters of [[CssDecs]]. */
  def apply(classStr: String, props: RArr[CssDecs]): CssClassesRule = new CssClassesRule(classStr, props)

  /** Factory apply method to create rule for single CSS class with [[RArr]] of [[CssDecs]]. There is a name overload which takes an [[RArr]] of [[CssDecs]]. */
  def apply(classStr: String, props: CssDecs*): CssClassesRule = new CssClassesRule(classStr, props.toArr)
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