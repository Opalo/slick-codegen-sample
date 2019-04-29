import sbt.librarymanagement.ModuleID

import scala.xml.transform.{RewriteRule, RuleTransformer}
import scala.xml.{Comment, Elem, Node => XN, NodeSeq => XNS}

object PomPostProcessor {

  def getPostProcessor(dependencies: Seq[ModuleID], scalaBinaryVersion: String): XN => XN = { input: XN =>
    new RuleTransformer(new RewriteRule {

      override def transform(node: XN): XNS = node match {
        case e: Elem if e.label == "dependency" && isGeneratorDependency(findChild(e, "groupId"), findChild(e, "artifactId")) =>
          def txt(label: String): String = "\"" + e.child.filter(_.label == label).flatMap(_.text).mkString + "\""
          Comment(s""" Generator dependency ${txt("groupId")} % ${txt("artifactId")} % ${txt("version")} has been omitted """)
        case _ => node
      }

      def isGeneratorDependency(organization: Option[XN], name: Option[XN]): Boolean = {
        (for {
          o <- organization
          n <- name
          v = dependencies.exists(d => d.organization == o.text && (d.name == n.text || s"${d.name}_$scalaBinaryVersion" == n.text))
        } yield v).getOrElse(false)
      }

      def findChild(e: Elem, lbl: String): Option[XN] = {
        e.child.find(_.label == lbl)
      }

    }).transform(input).head
  }

}
