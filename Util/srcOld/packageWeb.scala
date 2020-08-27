/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Currently looking at this package and considering ti for complete deletion and replacement with pXml.
 * 
 * This package for the manipulation of Html, XML CSS and Javascript. Some of the functionality could be replaced by Scalatags. As it currently 
 *  stands the code and design quality of this package is not up to standard, but due to many other higher priorities, I have not been able to take
 *  the next step, which is to 1 improve and document it, rewrite it from scratch or 3 replace it with a Scala-tags dependency or some other
 *  library. */
package object pWeb
{ implicit def indentConLikeString(s: String): IndText = IndText(s)  
}