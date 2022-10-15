/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Css Rule consisting of selector plus a set of declarations. */
trait CssRule
{ def selec: String
  def props: RArr[CssDec]
  def propsStr: String = props.ssLength match {
    case 0 => " {}"
    case 1 => s" { ${props.head.out} }"
    case _ => "\n{  " + props.foldStr(_.out, "\n  ") + " \n}"
  }
  def out: String = selec + propsStr
}

/** CSS rule for the body. */
case class CssBody(props: RArr[CssDec]) extends CssRule
{ override def selec: String = "body"
}

/** CSS rule for the H1 header. */
case class CssH1(props: RArr[CssDec]) extends CssRule
{ override def selec: String = "h1"
}