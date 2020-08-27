/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** I'm just trying out a new package, not sure whether will use pWeb. */
package object pWeb
{  
  def closedTagStr(tagName: String, attribs: Attrib *): String = attribs.foldLeft("<" + tagName)(_ + " " + _.str) + " />"
  def closedTagStr(tagName: String, attribs: Arr[Attrib]): String = attribs.foldLeft("<" + tagName)(_ + " " + _.str) + " />"
}  
