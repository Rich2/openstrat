/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML A anchor element. */
class AHtml(val linkStr: String, otherAttribs: RArr[HAtt], val contents: RArr[XCon]) extends HtmlInedit
{ override def tagName: String = "a"

  override val attribs: RArr[HAtt] = HrefAtt(linkStr) %: otherAttribs
}

object AHtml
{ /** Factory apply method for creating HTML anchor element. If you don't supply a label the link will be used as the label. */
  def apply(linkStr: String, label: String = ""): AHtml =
  { val label2 = ife(label == "", linkStr, label)
    new AHtml(linkStr, RArr(), RArr(label2))
  }

  /** Factory apply method for creating HTML anchor element. If you don't supply a label the link will be used as the label. */
  def apply(link: FileSystemPath, label: String): AHtml = apply(link.asStr, label)

  /** Factory apply method for creating HTML anchor element from an [[HtmlPageFile]] with a path to the file name. There is an apply overload without a path. */
  def apply(page: HtmlPageFile, pathStr: String): AHtml = new AHtml(pathStr / page.fileNameStr, RArr(), RArr(page.titleStr))

  /** Factory apply method for creating HTML anchor element from an [[HtmlPageFile]], with no path to the file name. There is an apply overload with a
   * [[String]] for the path. */
  def apply(page: HtmlPageFile): AHtml = new AHtml(page.fileNameStr, RArr(), RArr(page.titleStr))
}

/** HTML noscript element. */
case class NoScriptHtml(attribs: RArr[HAtt], contents: RArr[XCon]) extends HtmlOwnLine
{ override def tagName: String = "noscript"
}

object NoScriptHtml
{ /** Factory apply method for creating an HTML no-script element */
  def apply(): NoScriptHtml = new NoScriptHtml(RArr(), RArr("This page will not function properly without Javascript enabled"))
}

/** HTML script element. */
case class ScriptHtml(attribs: RArr[HAtt], contents: RArr[XCon]) extends HtmlOwnLine
{ override def tagName: String = "script"
}

/** Companion object for [[ScriptHtml]] class, HTML script element Contains factory methods for creating the src and function call elements. */
object ScriptHtml
{ /** Sets the link for a JavaScript script file. */
  def jsSrc(src: String): ScriptHtml = ScriptHtml(RArr(TypeJsAtt, SrcAtt(src)), RArr())

  /** Sets the function for an external JavaScript call. */
  def main(stem: String): ScriptHtml = ScriptHtml(RArr(TypeJsAtt), RArr(stem + ".main()"))
  
  def inlineJsStr(codeStr: String): ScriptHtml = ScriptHtml(RArr(TypeJsAtt), RArr(codeStr))
}

/** HTML style element. note there is also a CSS [[StyleAtt]] attribute. */
case class StyleHtml(attribs: RArr[HAtt], contents: RArr[CssRuleLike]) extends HtmlOwnLine
{ override def tagName: String = "style"
}

object StyleHtml
{ /** Factory apply method for [[StyleHtml]]. */
  def apply(rules: CssRuleLike*): StyleHtml = new StyleHtml(RArr(), rules.toArr)
}

/** HTML h1 header element. */
case class H1Html(attribs: RArr[HAtt], contents: RArr[XCon]) extends HtmlOwnLine
{ override def tagName = "h1"
}

object H1Html
{ /** HTML h1 header element. */
  def apply(contents: XCon*): H1Html = new H1Html(RArr(), contents.toRArr)
}

/** HTML h2 header element. */
case class H2Html(attribs: RArr[HAtt], contents: RArr[XCon]) extends HtmlOwnLine
{ def tagName = "h2"
}

object H2Html
{ /** HTML h2 header element. */
  def apply(contents: XCon*): H2Html = new H2Html(RArr(), contents.toRArr)
}

/** Html H3 header element. */
case class H3Html(attribs: RArr[HAtt], contents: RArr[XCon]) extends HtmlOwnLine
{ def tagName = "h3"
}

object H3Html
{ /** HTML h3 header element. */
  def apply(contents: XCon*): H3Html = new H3Html(RArr(), contents.toRArr)
}

/** Html H4 header element. */
case class H4Html(attribs: RArr[HAtt], contents: RArr[XCon]) extends HtmlOwnLine
{ def tagName = "h4"
}

object H4Html
{ /** HTML h1 header element. */
  def apply(contents: XCon*): H4Html = new H4Html(RArr(), contents.toRArr)
}

/** Html H4 header element. */
case class H5Html(attribs: RArr[HAtt], contents: RArr[XCon]) extends HtmlOwnLine
{ def tagName = "h5"
}

object H5Html
{ /** HTML h1 header element. */
  def apply(contents: XCon*): H5Html = new H5Html(RArr(), contents.toRArr)
}

/** Html H4 header element. */
case class H6Html(attribs: RArr[HAtt], contents: RArr[XCon]) extends HtmlOwnLine
{ def tagName = "h6"
}

object H6Html
{ /** HTML h6 header element. */
  def apply(contents: XCon*): H6Html = new H6Html(RArr(), contents.toRArr)
}

/** HTML button element. */
case class ButtonHtml(attribs: RArr[HAtt], contents: RArr[XCon]) extends HtmlOwnLine
{ override def tagName = "button"
}

object ButtonHtml
{ /** Factory apply method to create HTML button element. */
  def apply(contents: XCon*): ButtonHtml = new ButtonHtml(RArr(SubmitTypeAtt), contents.toRArr)
}