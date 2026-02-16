/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** CSS rule for HTML li list item. */
case class CssLi(decsArr: RArr[CssDecBase]) extends CssRule
{ override def selec: String = "li"
}

object CssLi
{ /** Factory apply method for CSS rule for li. */
  def apply(props: CssDecBase*): CssLi = new CssLi(props.toArr)
}

trait ListStyleVal extends CssVal

class ListStyleDec(value: ListStyleVal) extends CssDec
{ override def property: String = "list-style-type"
  override def valueStr: String = value.str
}

/** CSS list-style-type declaration set to value of none. */
object ListStyleNoneDec extends ListStyleDec(NoneCss)

/** Style attribute with a single CSS list-style-type declaration set to value of none. */
object ListStyleNoneAtt extends StyleAtt(RArr(ListStyleNoneDec))

