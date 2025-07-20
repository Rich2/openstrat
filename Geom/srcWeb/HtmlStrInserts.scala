/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Import the contents of this object for short named methods that can insert inline HTML code into a [[String]]. */
object HtmlStrInserts
{ /** Returns the output string for an HTML link. */
  def path(str: String): String = HtmlDirPath(str).out()

  /** Returns the output string for an HTML link. */
  def link(path: String, label: String = ""): String = HtmlA(path,label).out()

  /** Returns the output string for an HTML link. */
  def code(str: String): String = HtmlCodeInline(str).out()

  /** Returns the output string for an HTML link. */
  def sbt(str: String): String = HtmlSbtInline(str).out()
}