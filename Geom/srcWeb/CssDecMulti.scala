/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Declaration with a single value that is translated to multiple CSS declarations. */
trait CssDecMulti extends CssDecs

/** Css margin-lop and bottom declarations. */
case class CssMargTopBot(value: CssVal) extends CssDecMulti
{ override def decs: RArr[CssDec] = RArr(DecMargTop(value), DecMargBottom(value))
}

/** Css margin-left and margin-right declarations set to same value. */
case class DecMargLeftRight(value: CssVal) extends CssDecMulti
{ override def decs: RArr[CssDec] = RArr(DecMargLeft(value), DecMargRight(value))
}

/** Css margin-left and margin-right declarations set to auto. */
case object DecMargLeftRightAuto extends CssDecMulti
{ override def decs: RArr[CssDec] = RArr(DecMargLeft(CssAuto), DecMargRight(CssAuto))
}