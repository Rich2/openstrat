/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML A anchor element. */
class AHtml(val link: String, val contents: RArr[XCon], otherAttribs: RArr[XAtt] = RArr()) extends HtmlInedit
{ override def tagName: String = "a"
  override val attribs: RArr[XAtt] = RArr(HrefAtt(link)) ++ otherAttribs
}

object AHtml
{ /** Factory apply method for creating HTML anchor element. If you don't supply a label the link will be used as the label. */
  def apply(link: String, label: String = ""): AHtml =
  { val label2 = ife(label == "", link, label)
    new AHtml(link, RArr(label2))
  }

  /** Factory apply method for creating HTML anchor element. If you don't supply a label the link will be used as the label. */
  def apply(link: FileSystemPath, label: String): AHtml = apply(link.asStr, label)

  /** Factory apply method for creating HTML anchor element from an [[PageFile]] with a path to the file name. There is an apply overload without a path. */
  def apply(page: PageFile, pathStr: String): AHtml = new AHtml(pathStr / page.fileNameStr, RArr(page.titleStr))

  /** Factory apply method for creating HTML anchor element from an [[PageFile]], with no path to the file name. There is an apply overload with a
   * [[String]] for the path. */
  def apply(page: PageFile): AHtml = new AHtml(page.fileNameStr, RArr(page.titleStr))
}

/** HTML noscript element. */
case class NoScriptHtml(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()) extends HtmlOwnLine
{ override def tagName: String = "noscript"
}

object NoScriptHtml
{ /** Factory apply method for creating an HTML no-script element */
  def apply(): NoScriptHtml = new NoScriptHtml(RArr("This page will not function properly without Javascript enabled"))
}

/** HTML script element. */
case class ScriptHtml(contents: RArr[XCon], attribs: RArr[XAtt]) extends HtmlOwnLine
{ override def tagName: String = "script"
}

/** Companion object for [[ScriptHtml]] class, HTML script element Contains factory methods for creating the src and function call elements. */
object ScriptHtml
{ /** Sets the link for a JavaScript script file. */
  def jsSrc(src: String): ScriptHtml = ScriptHtml(RArr(), RArr(TypeJsAtt, SrcAtt(src)))

  /** Sets the function for an external JavaScript call. */
  def main(stem: String): ScriptHtml = ScriptHtml(RArr(stem + ".main()"), RArr(TypeJsAtt))
  
  def inlineJsStr(codeStr: String): ScriptHtml = ScriptHtml(RArr(codeStr), RArr(TypeJsAtt))
}

/** HTML style element. note there is also a CSS [[StyleAtt]] attribute. */
case class StyleHtml(contents: RArr[CssRuleLike], attribs: RArr[XAtt] = RArr()) extends HtmlOwnLine
{ override def tagName: String = "style"
}

object StyleHtml
{ /** Factory apply method for [[StyleHtml]]. */
  def apply(rules: CssRuleLike*): StyleHtml = new StyleHtml(rules.toArr)
}

/** HTML h1 header element. */
case class H1Html(str : String, attribs: RArr[XAtt] = RArr()) extends HtmlStrOwnLine
{ override def tagName = "h1"
}

/** HTML h2 header element. */
case class H2Html(str : String, attribs: RArr[XAtt] = RArr()) extends HtmlStrOwnLine
{ def tagName = "h2"
}

/** Html H3 header element. */
case class H3Html(str : String, attribs: RArr[XAtt] = RArr()) extends HtmlStrOwnLine
{ def tagName = "h3"
}

/** Html H4 header element. */
case class H4Html(str : String, attribs: RArr[XAtt] = RArr()) extends HtmlStrOwnLine
{ def tagName = "h4"
}

/** Html H4 header element. */
case class H5Html(str : String, attribs: RArr[XAtt] = RArr()) extends HtmlStrOwnLine
{ def tagName = "h5"
}

/** Html H4 header element. */
case class H6Html(str : String, attribs: RArr[XAtt] = RArr()) extends HtmlStrOwnLine
{ def tagName = "h6"
}

/** HTML button element. */
class ButtonHtml(val contents: RArr[XCon], val attribs: ostrat.RArr[XAtt] = RArr()) extends HtmlOwnLine
{ override def tagName = "button"
}

object ButtonHtml
{ /** Factory apply method to create HTML button element. */
  def apply(inp: String): ButtonHtml = new ButtonHtml(RArr(inp), RArr(TypeSubmitAtt))
}