package de.tum.in.test.api.util;

import de.tum.in.test.api.BlacklistPath;
import de.tum.in.test.api.PathActionLevel;
import de.tum.in.test.api.PathType;
import de.tum.in.test.api.WhitelistPath;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Objects;
import java.util.stream.Stream;

@API(status = Status.INTERNAL)
public final class PathRule {
    private final de.tum.in.test.api.util.RuleType ruleType;
    private final PathType pathType;
    private final PathActionLevel actionLevel;
    private final String pathPattern;
    private final PathMatcher pathMatcher;

    private PathRule(de.tum.in.test.api.util.RuleType ruleType, PathType pathType, PathActionLevel actionLevel, String pathPattern) {
        this.ruleType = ruleType;
        this.pathType = pathType;
        this.actionLevel = actionLevel;
        this.pathPattern = pathPattern;
        this.pathMatcher = this.pathType.convertToPathMatcher(this.pathPattern);
    }

    public static Stream<PathRule> allOf(WhitelistPath whitelistedPath) {
        return Stream.of(whitelistedPath.value()).map(pathPattern -> new PathRule(de.tum.in.test.api.util.RuleType.WHITELIST,
                whitelistedPath.type(), whitelistedPath.level(), pathPattern));
    }

    public static Stream<PathRule> allOf(BlacklistPath blacklistedPath) {
        return Stream.of(blacklistedPath.value()).map(pathPattern -> new PathRule(RuleType.BLACKLIST,
                blacklistedPath.type(), blacklistedPath.level(), pathPattern));
    }

    public de.tum.in.test.api.util.RuleType getRuleType() {
        return ruleType;
    }

    public PathType getPathType() {
        return pathType;
    }

    public PathActionLevel getActionLevel() {
        return actionLevel;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    public boolean matchesWithLevel(Path path, PathActionLevel request) {
        if (ruleType == de.tum.in.test.api.util.RuleType.BLACKLIST)
            return request.isAboveOrEqual(actionLevel) && pathMatcher.matches(path);
        return request.isBelowOrEqual(actionLevel) && pathMatcher.matches(path);
    }

    public boolean matchesRecursivelyWithLevel(Path path, PathActionLevel request) {
        // common ancestor must match
        if (!matchesWithLevel(path, request))
            return false;
        // then pattern must allow children
        return pathType.isPatternRecursive(pathPattern);
    }

    @Override
    public String toString() {
        return String.format("PathRule[\"%s\" (%s) in %s; level %s]", pathPattern, pathType, ruleType, actionLevel); //$NON-NLS-1$
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionLevel, pathPattern, pathType, ruleType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PathRule other))
            return false;
        return actionLevel == other.actionLevel && pathType == other.pathType && ruleType == other.ruleType
                && Objects.equals(pathPattern, other.pathPattern);
    }
}
