/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

package object wcode
{
  /** Returns the output string for an HTML file / directory path code element, plus a tailing [[String]]. */
  def dirOut(str: String, tailStr: String): String = HtmlDirPath(str).out() + tailStr

  extension (thisString: String) {/** Extension method to return an HTML file / directory path code element. */
    def htmlPath: HtmlDirPath = HtmlDirPath(thisString)

    /** Extension method to return an HTML sbt class code element. */
    def htmlSbt: HtmlSbtInline = HtmlSbtInline(thisString)

    /** Implicit method to return an HTML Scala element. */
    def htmlScala: HtmlScalaInline = HtmlScalaInline(thisString)

    /** Implicit method to return an HTML Bash element. */
    def htmlBash: BashInline = BashInline(thisString)
  }
}