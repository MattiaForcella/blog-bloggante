package co.develhope.team3.blog.visitors;

import org.springframework.stereotype.Component;

import it.pasqualecavallo.studentsmaterial.authorization_framework.security.RoleVisitor;

@Component
public class HierarchicalRoleVisitor extends RoleVisitor {

	@Override
	protected boolean isRoleInternalHierarchicallyUpperOrEqualsTo(String requiredRole, String userRoles) {
		switch(requiredRole) {
		case "ROLE_USER":
			return "ROLE_USER".equals(userRoles) || "ROLE_EDITOR".equals(userRoles) || "ROLE_ADMIN".equals(userRoles);
		case "ROLE_EDITOR":
			return "ROLE_EDITOR".equals(userRoles) || "ROLE_ADMIN".equals(userRoles) ;
		case "ROLE_ADMIN":
			return "ROLE_ADMIN".equals(userRoles);
		default:
			return false;
		}
	}

}
