/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

import org.w3c.dom.css.CSSValue

/** Css margin declaration. */
case class DecMarg(value: CssVal) extends CssDecStd
{ override def prop: String = "margin"
}

/** Css margin-top declaration. */
case class DecMargTop(value: CssVal) extends CssDecStd
{ override def prop: String = "margin-top"
}

/** Css margin-bottom declaration. */
case class DecMargBottom(value: CssVal) extends CssDecStd
{ override def prop: String = "margin-bottom"
}

/** Css margin-left declaration. */
case class DecMargLeft(value: CssVal) extends CssDecStd
{ override def prop: String = "margin-left"
}

/** Css margin-right declaration. */
case class DecMargRight(value: CssVal) extends CssDecStd
{ override def prop: String = "margin-right"
}

/** Css border declaration. */
case class DecBorder(value: CssVal) extends CssDecStd
{ override def prop: String = "border"
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
case class DecPad(value: CssVal) extends CssDecStd
{ override def prop: String = "padding"
}

/** Css padding-left declaration. */
case class DecPadLeft(value: CssVal) extends CssDecStd
{ override def prop: String = "padding-left"
}

/** Css padding-right declaration. */
case class DecPadRight(value: CssVal) extends CssDecStd
{ override def prop: String = "padding-right"
}