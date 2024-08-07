package com.bringup.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RolesType {
	ROLE_ADMIN, // 관리자
	ROLE_MEMBER, // 일반 회원
	ROLE_COMPANY; // 기업 회원

	@JsonCreator
	public static RolesType fromRequest(String inputString) {
		for (RolesType rolesType : RolesType.values()) {
			if (rolesType.toString().equals(inputString.toUpperCase())) {
				return rolesType;
			}
		}
		return null;
	}
}
