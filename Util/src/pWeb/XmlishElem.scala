/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

/** An XML or an HTML element */
trait XmlishElem
{ def tag: String
  def attribs: Arr[XmlAtt]
}
