/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

trait Syntax
{
  def outLines(indent: Int)(implicit prefs: SyntaxPrefs): RArr[String]
  def out(using prefs: SyntaxPrefs): String = outLines(0)(prefs).foldLeft("")(_ + '\n' + _)
}

case class SyntaxPrefs(maxLineLength: Int = 150, multiStatement: Boolean = false)