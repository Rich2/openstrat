/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

case class TextLines(text: String, numLines: Int, firstLen: Int)// extends TextLines
{ /** The length of the last line of this text. The purpose of this field is to determine if there is space to add further tokens on the same line. */
  def tailLen: Int = (text.length - firstLen - 1).max0
}