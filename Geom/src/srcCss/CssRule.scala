/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

trait CssRuleLike extends XConCompound
{ /** Outputs to  a single line if the rule has 2 or more declarations. */
  def isMultiLine: Boolean

  /** The CSS output. */
  def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = MaxLineLen): String
}

/** CSS Rule consisting of selector plus a set of declarations. */
trait CssRule extends CssRuleLike
{ /** The declarations for this CSS rule. */
  def decsArr: RArr[CssDecBase]

  /** The selector [[String]] for the CSS rule. */
  def selecStr: String

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
  override def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = MaxLineLen): String = selecStr + decsStr(indent)
  
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines =
  { val decs: RArr[CssDec] = decsArr.flatMap(_.decs)
    decs.length match
    { case 0 => TextLines(selecStr -- "{}")
      case 1 =>
      { val str = selecStr -- s" { ${decs.head.out} }"
        TextLines(str)
      }
      case 2 =>
      { val str = s" { ${decs(0).out} ${decs(1).out} }"
        TextLines(str)
      }
      case _ =>
      { val str = "\n" + (indent).spaces + "{ " + decs.mkStr(_.out, "\n" + (indent + 2).spaces) + "\n" + indent.spaces + "}"
        TextLines(str)
      }
    }
  }
}

object CssRule
{ /** Factory apply method for CSS rule. There is an apply overload where the [[CssDec]]s are passed as an [[RArr]]. */
  def apply(selector: CssPartialSelector, decArr: RArr[CssDecBase]): CssRule = CssRule1(selector, decArr)

  /** Factory apply method for CSS rule. There is an apply overload where the [[CssDec]]s are passed as an [[RArr]]. */
  def apply(selector: CssPartialSelector, decs: CssDecBase*): CssRule = CssRule1(selector, decs.toArr)

  /** Factory apply method for CSS rule. There is an apply overload where the [[CssDec]]s are passed as an [[RArr]]. */
  def apply(selectors: RArr[CssPartialSelector], decArr: RArr[CssDecBase]): CssRule = CssRuleMulti(selectors, decArr)
}

/** [[CssRule]] with a single selector. */
trait CssRule1 extends CssRule
{ /** The selector for this CSS rule. */
  def selector: CssSelector

  final override def selecStr: String = selector.cssOut
}

object CssRule1
{ /** Factory apply method to construct a [[CssRule]] with a single selector. */
  def apply(selector: CssSelector, decsArr: RArr[CssDecBase]): CssRule1 = new CssRule1Gen(selector, decsArr)

  /** Factory apply method to construct a [[CssRule]] with a single selector. */
  def apply(selector: CssSelector, decs: CssDecBase*): CssRule1 = new CssRule1Gen(selector, decs.toRArr)

  /** Implementation class for the general case of a [[CssRule1]] a [[CssRule]] with a single selector. */
  case class CssRule1Gen(selector: CssSelector, decsArr: RArr[CssDecBase]) extends CssRule1
}

/** [[CssRule]] with multiple selectors. */
case class CssRuleMulti(selectors: RArr[CssPartialSelector], decsArr: RArr[CssDecBase]) extends CssRule
{ final override def selecStr: String = selectors.mkStr(_.cssOut, ", ")
}

object CssRuleMulti
{ /** Factory apply method for CSS rule with . There is an apply overload where the [[CssDec]]s are passed as an [[RArr]]. */
  def apply(selectors: CssPartialSelector*)(decs: CssDecBase*): CssRuleMulti = new CssRuleMulti(selectors.toRArr, decs.toArr)
}

/** [[CssRule]] with a Descendant combinator selector. */
case class CssRuleDescent(ancestor: CssPartialSelector, descendent: CssPartialSelector, decsArr: RArr[CssDecBase]) extends CssRule1
{ override def selector: CssDescendant = CssDescendant(ancestor, descendent)
}

object CssRuleDescent
{ /** Factory apply method to construct [[CssRule]] with a Descendant combinator selector. */
  def apply(ancestor: CssPartialSelector, descendent: CssPartialSelector, decs: CssDecBase*): CssRuleDescent = new CssRuleDescent(ancestor, descendent, decs.toRArr)
}