/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom.*

/** base trait for [[CssDec]] and [[CssDecMulti]], certain scala declarations that translate to multiple CSS Declarations. */
sealed trait CssDecBase
{ /** The declaration sequence, for [[CssDec]] this will have a single member. */
  def decs: RArr[CssDec]
}

/** CSS declaration. This consists of a key-value pair. */
trait CssDec extends CssDecBase, OutElem
{ /** The CSS property */
  def property: String

  /** The CSS value output. */
  def valueStr: String

  /** The CSS code output. */
  override def out: String = property + ": " + valueStr + ";"

  override def decs: RArr[CssDec] = RArr(this)
}

/** Standard CSS declaration which takes a [[CssVal]] to produce the CSS value output. */
trait CssDecStd extends CssDec
{ /** The CSS declaration value */
  def value: CssVal

  final override def valueStr: String = value.str
}

/** Declaration with a single value that is translated to multiple CSS declarations. */
trait CssDecMulti extends CssDecBase