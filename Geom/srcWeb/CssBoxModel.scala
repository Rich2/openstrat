/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Css margin declaration. */
case class MarginDec(value: CssVal) extends CssDecStd
{ override def property: String = "margin"
}

/** Css margin-top declaration. */
case class MarginTopDec(value: CssVal) extends CssDecStd
{ override def property: String = "margin-top"
}

/** Css margin-bottom declaration. */
case class MarginBottomDec(value: CssVal) extends CssDecStd
{ override def property: String = "margin-bottom"
}

/** Css margin-left declaration. */
case class MarginLeftDec(value: CssVal) extends CssDecStd
{ override def property: String = "margin-left"
}

/** Css margin-right declaration. */
case class MarginRightDec(value: CssVal) extends CssDecStd
{ override def property: String = "margin-right"
}

/** Css border declaration. */
case class BorderDec(value: CssVal) extends CssDecStd
{ override def property: String = "border"
}

object BorderDec
{ /** Factory apply method for CSS border declaration */
  def apply(valuesStr: String): BorderDec = new BorderDec(CssVal(valuesStr))
}

/** Css border-style value. */
trait BorderStyle extends CssVal
{ /** apply method combines border style with colour." */
  def apply(colour: Colour): CssVal = CssVal(str -- colour.webStr)
}

/** Css solid border style value. */
object CssSolid extends BorderStyle
{ override def str: String = "solid"
}

/** Css padding declaration. */
case class PaddingDec(value: CssVal) extends CssDecStd
{ override def property: String = "padding"
}

/** Css padding-left declaration. */
case class DecPadLeftDec(value: CssVal) extends CssDecStd
{ override def property: String = "padding-left"
}

/** Css padding-right declaration. */
case class PadRightDec(value: CssVal) extends CssDecStd
{ override def property: String = "padding-right"
}

/** Css padding-top declaration. */
case class PadTopDec(value: CssVal) extends CssDecStd
{ override def property: String = "padding-top"
}
/** Css padding-bottom declaration. */
case class PadBottomDec(value: CssVal) extends CssDecStd
{ override def property: String = "padding-bottom"
}