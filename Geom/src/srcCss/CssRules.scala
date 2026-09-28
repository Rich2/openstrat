/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** CSS rule for the body. */
case class BodyRule(decsArr: RArr[CssDecBase]) extends CssRule1
{ override def selector: CssSelector = BodyHtml
}

object BodyRule
{ /** Factory apply method for CSS rule for the HTML body. */
  def apply(props: CssDec*): BodyRule = new BodyRule(props.toArr)
}

/** CSS rule for HTML p paragraphs. */
case class PRule(decsArr: RArr[CssDecBase]) extends CssRule1
{ override def selector: CssSelector = PHtml
}

object PRule
{ /** Factory apply method for CSS rule for p HTML paragraph elements. */
  def apply(props: CssDecBase*): PRule = new PRule(props.toArr)
}

/** CSS rule for HTML li list item. */
case class LiRule(decsArr: RArr[CssDecBase]) extends CssRule1
{ override def selector: CssSelector = LiHtml
}

/** CSS rule for HTML h1 header elements. */
case class H1Rule(decsArr: RArr[CssDecBase]) extends CssRule1
{ override def selector: CssSelector = H1Html
}

object H1Rule
{ /** Factory apply method for CSS rule for h1 HTML header elements. */
  def apply(props: CssDec*): H1Rule = new H1Rule(props.toArr)
}