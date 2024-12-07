package de.tum.in.test.api.ast.type;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.LocalRecordDeclarationStmt;
import de.tum.in.test.api.ast.asserting.UnwantedNodesAssert;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.util.Map;

import static de.tum.in.test.api.localization.Messages.localized;

/**
 * Enumerates class Java statements which can be checked using
 * {@link UnwantedNodesAssert}.
 *
 * @author Markus Paulsen
 * @version 1.0.0
 * @since 1.12.0
 */
@API(status = Status.MAINTAINED)
public enum ClassType implements Type {
    /**
     * All class types
     */
    ANY(Map.of(localized("ast.enum.class_type.class"), LocalClassDeclarationStmt.class, //$NON-NLS-1$
            localized("ast.enum.class_type.record"), LocalRecordDeclarationStmt.class)),

    /**
     * The local class type (statements of the form: "class" + class name + "{" +
     * "}")
     */
    CLASS(Map.of(localized("ast.enum.class_type.class"), LocalClassDeclarationStmt.class)), //$NON-NLS-1$

    /**
     * The local record type (statements of the form: "record" + record name + "(" +
     * record attributes + ")" + "{" + "}")
     */
    RECORD(Map.of(localized("ast.enum.class_type.record"), LocalRecordDeclarationStmt.class)); //$NON-NLS-1$

    private final Map<String, Class<? extends Node>> nodeNameNodeMap;

    ClassType(Map<String, Class<? extends Node>> nodeNameNodeMap) {
        this.nodeNameNodeMap = nodeNameNodeMap;
    }

    @Override
    public Map<String, Class<? extends Node>> getNodeNameNodeMap() {
        return nodeNameNodeMap;
    }
}