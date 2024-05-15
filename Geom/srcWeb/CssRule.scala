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
  def out: String = selec + propsStr
}

trait CssRules
{
  def rules: RArr[CssRule]
  def endStr: String = ""

  def apply(): String = rules.foldStr(_.out, "\n\n") ---- endStr
}

/** CSS rule for the body. */
case class CssBody(props: RArr[CssDec]) extends CssRule
{ override def selec: String = "body"
}

object CssBody
{
  def apply(props: CssDec*): CssBody = new CssBody(props.toArr)
}

/** CSS rule for the H1 header. */
case class CssH1(props: RArr[CssDec]) extends CssRule
{ override def selec: String = "h1"
}

object CssH1
{
  def apply(props: CssDec*): CssH1 = new CssH1(props.toArr)
}

/** CSS rule for the OL ordered list. */
case class CssOl(props: RArr[CssDec]) extends CssRule
{ override def selec: String = "ol"
}

object CssOl
{
  def apply(props: CssDec*): CssOl = new CssOl(props.toArr)
}