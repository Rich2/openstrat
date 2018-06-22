/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Not sure if this package contains anything that is long term useful. Parsing of persisted files being done in the root of the ostrat package. I may
 *  replace this with Scalatags. */
package object pParse
{ 
   implicit def indentConLikeString (s: String): IndText = IndText(s)  
}