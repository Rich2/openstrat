/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Declaration with a single value that is translated to multiple CSS declarations. */
trait CssDecMulti extends CssDecs

/** Css margin-lop and bottom declarations. */
case class MargTopBottomDec(value: CssVal) extends CssDecMulti
{ override def decs: RArr[CssDec] = RArr(MarginTopDec(value), MarginBottomDec(value))
}

/** Css margin-left and margin-right declarations set to same value. */
case class MargLeftRightDec(value: CssVal) extends CssDecMulti
{ override def decs: RArr[CssDec] = RArr(MarginLeftDec(value), MarginRightDec(value))
}

/** Css margin-left and margin-right declarations set to auto. */
case object MarginLRAutoDec extends CssDecMulti
{ override def decs: RArr[CssDec] = RArr(MarginLeftDec(AutoCss), MarginRightDec(AutoCss))
}