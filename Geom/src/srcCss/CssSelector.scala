/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

sealed trait CssSelector
{
  /** The CSS code output. */
  def cssOut: String

  final def rule(decs: RArr[CssDecBase]): CssRule1 = CssRule1(this, decs)

  final def rule(decs: CssDecBase*): CssRule1 = CssRule1(this, decs.toRArr)
}

/** CSS Universal selector */
object CssUniversal extends CssSelector
{ /** The CSS code output. */
  override def cssOut: String = "*"
}

/** CSS selector that is not the universal selector. */
trait CssPartialSelector extends CssSelector
{ /** Constructs a CSS descendant selector. */
  final def desc(descendant: CssSelector): CssDescendant = CssDescendant(this, descendant)
  
  /** Constructs a CSS child selector. */
  final def child(child: CssSelector): CssChild = CssChild(this, child)

  /** Constructs a CSS next sibling selector. */
  final def next(junior: CssSelector): CssNextSibling = CssNextSibling(this, junior)

  /** Constructs a CSS Subsequent selector. */
  final def subs(junior: CssSelector): CssSubsSibling = CssSubsSibling(this, junior)
  
  
}

trait CssSimpleSelector extends CssPartialSelector

/** HTML tag selector for CSS */
trait HtmlTag extends CssSimpleSelector
{ /** The HTML tag. */
  def tag: String
  
  override def cssOut: String = tag
}

/** CSS Descendant selector. */
case class CssDescendant(ancestor: CssPartialSelector, descendent: CssSelector) extends CssPartialSelector
{ override def cssOut: String = ancestor.cssOut -- descendent.cssOut
}

/** CSS Child > selector. */
case class CssChild(parent: CssPartialSelector, child: CssSelector) extends CssPartialSelector
{ override def cssOut: String = parent.cssOut -- ">" -- child.cssOut
}

/** CSS Next Sibling + selector. */
case class CssNextSibling(elder: CssPartialSelector, junior: CssSelector) extends CssPartialSelector
{ override def cssOut: String = elder.cssOut -- "+" -- junior.cssOut
}

/** CSS Subsequent sibling ~ selector. */
case class CssSubsSibling(elder: CssPartialSelector, junior: CssSelector) extends CssPartialSelector
{ override def cssOut: String = elder.cssOut -- "~" -- junior.cssOut
}