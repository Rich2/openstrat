/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Currently can't think of a better name for this trait */
sealed trait ShowStyle
{
  def full: ShowStyle
}

/** Show the object just as its comma separated constituent values. */
case object ShowCommas extends ShowStyle
{ override def full: ShowStyle = ShowStd
}

/** Show the object as semicolon separated constituent values. */
case object ShowSemis extends ShowStyle
{ override def full: ShowStyle = ShowStd
}

/** Show the object in the standard default manner. */
case object ShowStd extends ShowStyle
{ override def full: ShowStyle = ShowStd
}

/** Show the object in the standard default manner, with parameter names. */
case object ShowFieldNames extends ShowStyle
{ override def full: ShowStyle = ShowFieldNames
}

/** Show the object as semicolon separated constituent values preceded b y their parameter names. */
case object ShowSemisNames extends ShowStyle
{ override def full: ShowStyle = ShowFieldNames
}

/** Show the object in the standard default manner, with field names and their types. */
case object ShowStdTypedFields extends ShowStyle
{ override def full: ShowStyle = ShowStdTypedFields
}

/** Show the object with the type of the object even if the string representation does not normally states its type. Eg Int(7). */
case object ShowTyped extends ShowStyle
{ override def full: ShowStyle = ShowTyped
}

/** Represents the object with the minimum text to specify. */
case object ShowMinimum extends ShowStyle
{ override def full: ShowStyle = ShowUnderScore
}

/** Represents the object with an underscore. */
case object ShowUnderScore extends ShowStyle
{ override def full: ShowStyle = ShowUnderScore
}