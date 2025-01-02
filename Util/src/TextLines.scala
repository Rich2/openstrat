/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

sealed trait TextLines
{ def text: String
}

trait TextInLines extends TextLines
{ /** The length of the last line of this text. The purpose of this field is to determine if there is space to add further tokens on the same line. */
  def lastLineLen: Int
}

/** Contains text from a structure of a human / programming language that continues a line and finishes on the same line. */
case class TextIn1Line(text: String, lastLineLen: Int) extends TextInLines

/** Contains text from a structure of a human / programming language that continues a line and finishes on the 2nd line same line. The second line is
 *  indented. */
case class TextIn2Line(text: String, lastLineLen: Int) extends TextInLines

/** Contains text from a structure of a human / programming languageProduces text that continues a line and finishes on the 3rd line same line. The
 *  second and third lines are indented. */
case class TextInMultiLines(text: String, lastLineLen: Int) extends TextInLines

/** Contains text from a structure of a human / programming languageProduces text that starts on its own line. It is intended that any following text
 *  should continue on its own line. All lines are indented. */
case class TextOwnLines(text: String, numLines: Int) extends TextLines