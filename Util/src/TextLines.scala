/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Class for returning output from a syntax hierarchy such as XML. HTML, CSS, JAvaScript, C ode etc. The class is created with a given max line length and an
 * indentation line. The second and subsequent lines will be indented to the given level. Indentation for the first line is the responsibility of the calling
 * object. */
case class TextLines(text: String, numLines: Int, firstLen: Int, lastLen: Int)