/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

trait Syntax
{
  def outLines(indent: Int)(implicit prefs: SyntaxPrefs): Arr[String]
  def out(implicit prefs: SyntaxPrefs): String = outLines(0)(prefs).foldLeft("")(_ + _)
}

case class SyntaxPrefs(maxLineLength: Int = 150, multiStatement: Boolean = false)