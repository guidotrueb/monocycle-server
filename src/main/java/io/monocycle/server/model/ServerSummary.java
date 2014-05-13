package io.monocycle.server.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ServerSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer coreCount;

	private Long totalMemory;

	private Long totalSwap;

	private String vendorName;

	private String vendorDescription;

	@ElementCollection
	public Set<MountPointSummary> mountPoints;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCoreCount() {
		return coreCount;
	}

	public void setCoreCount(Integer coreCount) {
		this.coreCount = coreCount;
	}

	public Long getTotalSwap() {
		return totalSwap;
	}

	public void setTotalSwap(Long totalSwap) {
		this.totalSwap = totalSwap;
	}

	public Long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(Long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorDescription() {
		return vendorDescription;
	}

	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}

	public Set<MountPointSummary> getMountPoints() {
		return mountPoints;
	}

	public void setMountPoints(Set<MountPointSummary> mountPoints) {
		this.mountPoints = mountPoints;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Embeddable
	public static class MountPointSummary {

		private String device;

		private String mountPoint;

		public String getDevice() {
			return device;
		}

		public void setDevice(String device) {
			this.device = device;
		}

		public String getMountPoint() {
			return mountPoint;
		}

		public void setMountPoint(String mountPoint) {
			this.mountPoint = mountPoint;
		}

	}

	@Override
	public String toString() {
		return "ServerSummary [id=" + id + ", coreCount=" + coreCount + ", totalMemory=" + totalMemory + ", vendorName=" + vendorName
				+ ", vendorDescription=" + vendorDescription + ", mountPoints=" + mountPoints + ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + "]";
	}

}
