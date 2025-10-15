/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait CssRuleLike extends XConCompound
{ /** Outputs to  a single line if the rule has 2 or more declarations. */
  def isMultiLine: Boolean

  /** The CSS output. */
  def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = MaxLineLen): String
}

/** Css Rule consisting of selector plus a set of declarations. */
trait CssRule extends CssRuleLike
{ /** The selector [[String]] for the CSS rule. */
  def selec: SelOrStr

  def selecStr: String = selec.outStr

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
{ /** Factory apply method for CSS rule. There is an apply overload where the [[CSSDec]]s are passed as repeat parameters. */
  def apply(selecIn: String, propsIn: RArr[CssDecs]): CssRule = new CssRule
  { override def selec: String = selecIn
    override val decsArr: RArr[CssDecs] = propsIn
  }

  /** Factory apply method for CSS rule. There is an apply overload where the [[CSSDec]]s are passed as an [[RArr]]. */
  def apply(selecIn: SelOrStr, propsIn: CssDecs*): CssRule = new CssRule
  { override def selec: SelOrStr = selecIn
    override def decsArr: RArr[CssDecs] = propsIn.toArr
  }
}

class CssChild(val parent: SelMemOrStr, val child: SelMemOrStr, val  decsArr: RArr[CssDecs]) extends CssRule
{ /** The selector [[String]] for the CSS rule. */
  override def selec: String = parent.outStr -- ">" -- child.outStr
}

object CssChild
{
  def apply(parent: SelMemOrStr, child: SelMemOrStr, decs: CssDecs*): CssChild = new CssChild(parent, child, decs.toArr)
}

class CssListRule(selectors: RArr[SelOrStr], val decsArr: RArr[CssDecs]) extends CssRule
{ /** The selector [[String]] for the CSS rule. */
  override def selec: CssSel | String = selectors.mkStr(_.outStr, ", ")
}

object CssListRule
{
  def apply(sel0: SelMemOrStr, others: SelMemOrStr*)(decs: CssDec*): CssListRule = new CssListRule(sel0 %: others.toArr, decs.toArr)
}