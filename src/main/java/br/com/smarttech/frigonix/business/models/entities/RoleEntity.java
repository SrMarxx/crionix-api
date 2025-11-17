package br.com.smarttech.frigonix.business.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_roles")
public class RoleEntity {
    @Id
    @Column(name = "role_id")
    private long roleId;
    private String name;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum Values{
        CRIAR(1L),
        VISUALIZAR(2L);

        long roleId;

        Values(long roleId){
            this.roleId = roleId;
        }

        public long getRoleId() {
            return roleId;
        }

        public void setRoleId(long roleId) {
            this.roleId = roleId;
        }
    }
}