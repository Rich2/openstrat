/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait ListStyleVal extends CssVal

class ListStyleDec(value: ListStyleVal) extends CssDec
{ override def property: String = "list-style-type"
  override def valueStr: String = value.str
}

object ListStyleNoneDec extends ListStyleDec(NoneCss)

object ListStyleNoneAtt extends StyleAtt(RArr(ListStyleNoneDec))