import type { Candidate } from "./Candidate"

export type Applicant = {
    candidate : Candidate
    applicationId: string,
    status: string
}